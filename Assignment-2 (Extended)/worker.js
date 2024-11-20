let paused = false;
let receivedBytes = 0;
let chunks = [];
let abortController = null;
let fileUrl="";
let fileId="";




self.onmessage = async (e) => {
    
  const { action, url, id, receivedBytes: startBytes } = e.data;

  if (action === "pause") {
    paused = true;
    if (abortController) 
        abortController.abort(); 
    return;
  }

  if (action === "resume") {
    paused = false;
    await downloadFile(fileUrl, fileId, receivedBytes);
    return;
  }

  if (action === "start") {
    paused = false;
    receivedBytes = startBytes || 0; 
    chunks = [];
    fileId=id;
    fileUrl=url
    await downloadFile(fileUrl, fileId, receivedBytes);
  }
};



async function downloadFile(url, id, startBytes) {
  try {
    abortController = new AbortController();
    const headers = startBytes > 0 ? { Range: `bytes=${startBytes}-` } : {};
    const response = await fetch(url, { headers, signal: abortController.signal });

    if (!response.ok && response.status !== 206) { // 206 means Partial Content (for long range requests)
      self.postMessage({ type: "error", error: response.statusText, id });
      return;
    }

    const reader = response.body.getReader();
    const contentLength = parseInt(response.headers.get("Content-Length")) + startBytes;

    while (!paused) {
      const { done, value } = await reader.read();
      if (done) break;

      chunks.push(value);
      receivedBytes += value.length;

      const progress = Math.round((receivedBytes / contentLength) * 100);
      self.postMessage({ type: "progress", progress, id, downloadedBytes: receivedBytes });
    }

    if (!paused) {
      const blob = new Blob(chunks);
      self.postMessage({ type: "completed", blob, id });
    }
  } catch (error) {
    if (!paused) {
      self.postMessage({ type: "error", error: error.message, id });
    }
  }
}






