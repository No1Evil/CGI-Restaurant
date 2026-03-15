SELECT count(1) from "reservations" r
where r.table_id = ?
and r.starts_at < ?
and r.ends_at > ?
and ends_at > NOW()
and r.is_deleted = false;