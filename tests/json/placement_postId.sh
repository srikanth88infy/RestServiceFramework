source ./config.sh
URL=http://localhost:8080/api/placements/$1
echo "accessing $URL"
curl -H "$HEADERS" -d "$PLACEMENT_POST_DATA" -D - -X POST $URL
echo ""
