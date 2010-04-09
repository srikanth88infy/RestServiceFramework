source ./config.sh
URL=http://localhost:8080/api/racks/$1
echo "accessing $URL"
curl -H "$HEADERS" -d "$RACK_PUT_DATA" -D - -X PUT $URL
echo ""
