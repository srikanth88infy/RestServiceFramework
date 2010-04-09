source ./config.sh
URL=http://localhost:8080/api/racks/$1
echo "accessing $URL"
curl -H "$HEADERS" -d "$RACK_POST_DATA" -D - -X POST $URL
echo ""
