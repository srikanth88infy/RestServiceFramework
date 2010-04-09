source ./config.sh

curl -H "$HEADERS" -d "$PLACEMENT_POST_DATA" -D - http://localhost:8080/api/placements
echo ""

