source ./config.sh
URL=http://localhost:8080/api/placement/$1
echo "accessing $URL"
curl -H "$HEADERS" -d "$PLACEMENT_PUT_DATA" -D - -X PUT $URL
echo ""
