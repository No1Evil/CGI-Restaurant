select t.* from "tables" t
where (? is null or t.zone_id = ?)
and (? is null or t.capacity >= ?)
and t.is_deleted = false
and not exists(
    select 1
    from "reservations" r
    where r.table_id = t.id
        and r.reservation_start < ?
        and r.reservation_end > ?
        and r.is_deleted = false
)