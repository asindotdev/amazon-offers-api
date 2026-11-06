// Read Every Offer from Amazon – Node.js example (Node 18+, ES module)
// Usage: ASIN_API_KEY=your-key node read_offers.mjs

const headers = { Authorization: `Bearer ${process.env.ASIN_API_KEY}`, "Content-Type": "application/json" };

const response = await fetch("https://www.asin.dev/api/v1/offers", {
  method: "POST",
  headers,
  body: JSON.stringify({
  "asin": "B09DFCB66S",
  "marketplace": "com"
}),
});
if (!response.ok) {
  console.error(`HTTP ${response.status}: ${await response.text()}`);
  process.exit(1);
}

console.log(JSON.stringify(await response.json(), null, 2));
