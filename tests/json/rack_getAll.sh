source ./config.sh
curl -H "$HEADERS" -D - http://localhost:8080/api/racks/
echo ""
