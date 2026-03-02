SELECT count(1) from "reservations" r
where r.table_id = ?
and (r.reservation_start < ? and r.reservation_end > ?)
and r.is_deleted = false;