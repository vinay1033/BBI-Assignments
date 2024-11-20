const downloadList = document.getElementById("download-list");
const addDownloadButton = document.getElementById("addDownload");
const videoUrlInput = document.getElementById("videoUrl");

// Regular expression for validating URL
const urlRegex = /^(https?:\/\/)?([a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)+)(:[0-9]+)?(\/.*)?(\?.*)?(#.*)?$/;

addDownloadButton.addEventListener("click", () => {
  const url = videoUrlInput.value.trim();

  if (!url) {
    alert("Please enter a URL.");
    return;
  }

  if (!isValidUrl(url)) {
    alert("Invalid URL. Please enter a valid video URL starting with http or https.");
    return;
  }

  createDownloadItem(url);
  videoUrlInput.value = ""; 
});

// Function to validate URLs
function isValidUrl(url) {
  return urlRegex.test(url);
}

function createDownloadItem(url) {
  const itemId = `download-${Date.now()}`;
  const worker = new Worker("worker.js");
  let isPaused = false;
  let isOffline = false;

  // Create DOM elements for download item
  const downloadItem = document.createElement("div");
  downloadItem.className = "download-item";
  downloadItem.innerHTML = `
    <p><strong>${url}</strong></p>
    <div class="progress-container">
      <div class="progress-bar" id="progress-${itemId}"></div>
    </div>
    <button class="start" id="start-${itemId}">Start</button>
    <button class="pause" id="pause-${itemId}" disabled>Pause</button>
  `;

  // Append to download list
  downloadList.appendChild(downloadItem);

  const startButton = document.getElementById(`start-${itemId}`);
  const pauseButton = document.getElementById(`pause-${itemId}`);
  const progressBar = document.getElementById(`progress-${itemId}`);

  let receivedBytes = 0;

  // Start button logic
  startButton.addEventListener("click", () => {
    if (navigator.onLine) {
      worker.postMessage({
        action: "start",
        url,
        id: itemId,
        receivedBytes, 
      });
      startButton.disabled = true;
      pauseButton.disabled = false;
    } else {
      alert("No internet connection. Please check your connection.");
    }
  });


  // Pause/Resume button logic
  pauseButton.addEventListener("click", () => {
    isPaused = !isPaused;
    worker.postMessage({ action: isPaused ? "pause" : "resume" });
    pauseButton.textContent = isPaused ? "Resume" : "Pause";
  });


  // Worker message handling
  worker.onmessage = (e) => {
    const { type, progress, blob, error, downloadedBytes } = e.data;

    if (type === "progress") {
      progressBar.style.width = `${progress}%`;
    } else if (type === "completed") {
      const blobUrl = URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = blobUrl;
      link.download = `video-${itemId}.mp4`;
      link.textContent = "Click to Download Video";
      link.style.display = "block";
      downloadItem.appendChild(link);
      pauseButton.disabled = true;
    } else if (type === "error") {
      alert(`Error: ${error}`);
      startButton.disabled = false;
      pauseButton.disabled = true;
    }

    
    // Update receivedBytes for resuming after a pause or Resume
    if (type === "progress") {
      receivedBytes = downloadedBytes;
    }
  };




  // Handle online/offline events to pause/resume downloads
  function handleOffline() {
    isOffline = true;
    if (!isPaused) {
      worker.postMessage({ action: "pause" });
      alert("You are offline. The download has been paused.");
      pauseButton.disabled = true;
    }
  }

  function handleOnline() {
    if (isOffline) {
      isOffline = false;
      if (!isPaused) {
        worker.postMessage({ action: "resume" });
        alert("You are back online. The download will resume.");
        pauseButton.disabled = false;
      }
    }
  }

  
  window.addEventListener("offline", handleOffline);
  window.addEventListener("online", handleOnline);
}
