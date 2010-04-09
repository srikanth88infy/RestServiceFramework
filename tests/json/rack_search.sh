source ./config.sh

URL=http://localhost:8080/api/racks/search
echo "accessing $URL"
curl -H "$HEADERS" -D - -d "name=XXX" -X POST $URL
echo ""
