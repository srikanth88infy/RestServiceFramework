source ./config.sh
curl -H "$HEADERS" -D - -X DELETE http://localhost:8080/api/items/
echo ""
