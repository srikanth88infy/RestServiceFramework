source ./config.sh

curl -H "$HEADERS" -d "$RACK_POST_DATA" http://localhost:8080/api/racks
echo ""

