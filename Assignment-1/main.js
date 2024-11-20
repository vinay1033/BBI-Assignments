
const singleBtn = document.getElementById('single-btn');
const multiBtn = document.getElementById('multi-btn');
const cancelBtn = document.getElementById('cancel-btn');
const output = document.getElementById('output');
const progressBar = document.getElementById('progress-bar');
let workers = [];  

function updateProgressBar(percentage) {
  progressBar.style.width = `${percentage}%`;
}

/**
 * Check if a number is prime
 */
function isPrime(num) {
  if (num < 2) return false;
  for (let i = 2; i <= Math.sqrt(num); i++) {
    if (num % i === 0) return false;
  }
  return true;
}

/**
 * Single-threaded prime number calculation
 */
singleBtn.addEventListener('click', () => {
  const limit = parseInt(document.getElementById('limit').value, 10);
  output.textContent = 'Calculating primes (single-threaded)...';

  const start = performance.now();
  const primes = [];
  const numCores = 1; 

  for (let i = 2; i <= limit; i++) {
    if (isPrime(i)) primes.push(i);

    // Update progress bar every 1% of the range
    if (i % Math.ceil(limit / 100) === 0) {
      updateProgressBar((i / limit) * 100);
    }
  }

  const duration = (performance.now() - start).toFixed(2);
  output.textContent = `Using ${numCores} core(s). Found ${primes.length} primes in ${duration} ms (single-threaded).`;
  updateProgressBar(100); 
});

/**
 * Multi-threaded prime number calculation using Web Workers
 */
multiBtn.addEventListener('click', () => {
  const limit = parseInt(document.getElementById('limit').value, 10);
  const numWorkers = navigator.hardwareConcurrency || 4;
  const rangeSize = Math.ceil(limit / numWorkers);

  let completedWorkers = 0;
  let primes = []; 
  workers = []; 



  output.textContent = 'Calculating primes (multi-threaded)...';
  updateProgressBar(0); 
  const start = performance.now();

  // Display the number of cores used
  const numCores = numWorkers;
  
  // Create and assign tasks to workers
  for (let i = 0; i < numWorkers; i++) {
    const startRange = i * rangeSize + 1;
    const endRange = Math.min((i + 1) * rangeSize, limit);

    console.log(startRange+" "+endRange);

    const worker = new Worker('worker-thread.js');
    workers.push(worker);

    worker.postMessage({ start: startRange, end: endRange });

    // Handle messages from workers
    worker.onmessage = (e) => {
      primes = primes.concat(e.data);
      completedWorkers++;

      // Update progress bar based on completed workers
      const progress = (completedWorkers / numWorkers) * 100;
      updateProgressBar(progress);

      // When all workers are done
      if (completedWorkers === numWorkers) {
        const duration = (performance.now() - start).toFixed(2);
        output.textContent = `Using ${numCores} core(s). Found ${primes.length} primes in ${duration} ms (multi-threaded).`;
        updateProgressBar(100); 
        cancelBtn.disabled = true;
      }
    };

    // Handle worker errors
    worker.onerror = (err) => {
      output.textContent = `Error in worker: ${err.message}`;
      cancelAllWorkers();
    };
  }

  cancelBtn.disabled = false; 
});

/**
 * Cancel all active workers
 */
cancelBtn.addEventListener('click', () => {
  cancelAllWorkers();
  output.textContent = 'Calculation cancelled.';
  updateProgressBar(0); 
  cancelBtn.disabled = true;
});

/**
 * Terminate all active workers
 */
function cancelAllWorkers() {
  workers.forEach((worker) => worker.terminate());
  workers = [];
}
