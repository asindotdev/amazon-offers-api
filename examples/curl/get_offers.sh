#!/usr/bin/env bash
# Offers Already Known – cURL example
# Usage: ASIN_API_KEY=your-key bash get_offers.sh
set -euo pipefail

curl -sS --fail-with-body -G "https://www.asin.dev/api/v1/offers" \
  -H "Authorization: Bearer ${ASIN_API_KEY}" \
  --data-urlencode asin=B09DFCB66S \
  --data-urlencode marketplace=com
