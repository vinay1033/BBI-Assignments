self.onmessage = (e) => {
    const { start, end } = e.data;
    const primes = [];
  
    for (let i = start; i <= end; i++) {
      if (isPrime(i)) primes.push(i);
    }
  
    self.postMessage(primes);
  };
  
  function isPrime(num) {
    if (num < 2) return false;
    for (let i = 2; i <= Math.sqrt(num); i++) {
      if (num % i === 0) return false;
    }
    return true;
  }
  