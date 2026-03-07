MERGE INTO "user_restaurant_permissions" (user_id, restaurant_id, role, date_updated)
KEY (user_id, restaurant_id)
VALUES (?, ?, ?, CURRENT_TIMESTAMP);