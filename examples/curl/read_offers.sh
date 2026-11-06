#!/usr/bin/env bash
# Read Every Offer from Amazon – cURL example
# Usage: ASIN_API_KEY=your-key bash read_offers.sh
set -euo pipefail

curl -sS --fail-with-body -X POST "https://www.asin.dev/api/v1/offers" \
  -H "Authorization: Bearer ${ASIN_API_KEY}" \
  -H "Content-Type: application/json" \
  --data '{"asin": "B09DFCB66S", "marketplace": "com"}'
