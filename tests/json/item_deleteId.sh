source ./config.sh
URL=http://localhost:8080/api/items/$1
echo "accessing $URL"
curl -H "$HEADERS" -D - -X DELETE $URL
echo ""
