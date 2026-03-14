select t.* from "tables" t
where (:zoneId IS NULL OR t.zone_id = :zoneId)
and (:restaurantId IS NULL OR t.restaurant_id = :restaurantId)
and (:capacity IS NULL OR t.capacity >= :capacity)
and t.is_deleted = false
and not exists(
    select 1
    from "reservations" r
    where r.table_id = t.id
        and r.reservation_start < :end
        and r.reservation_end > :start
        and r.is_deleted = false
)