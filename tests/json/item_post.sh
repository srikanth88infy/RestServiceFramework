source ./config.sh

curl -H "$HEADERS" -d "$ITEM_POST_DATA" http://localhost:8080/api/items
echo ""

