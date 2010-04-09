source ./config.sh
URL=http://localhost:8080/api/items
echo "accessing $URL"
echo "this should fail, we don't allow updating whole resources"
curl -H "$HEADERS" -d "$ITEM_PUT_DATA" -D - -X PUT $URL
echo ""
