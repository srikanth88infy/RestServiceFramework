source ./config.sh

curl -H "$HEADERS" -d "$ITEM_POST_DATA" http://10.0.101.10:8080/api/items
echo ""
curl -H "$HEADERS" -d "$RACK_POST_DATA" http://10.0.101.10:8080/api/items
echo ""
curl -H "$HEADERS" -d "$PLACEMENT_POST_DATA" http://10.0.101.10:8080/api/items
echo ""


curl -H "$HEADERS" -d "$ITEM_POST_DATA1" http://10.0.102.10:8080/api/items
echo ""

curl -H "$HEADERS" -d "$RACK_POST_DATA1" http://10.0.102.10:8080/api/items
echo ""

curl -H "$HEADERS" -d "$PLACEMENT_POST_DATA1" http://10.0.102.10:8080/api/items
echo ""


