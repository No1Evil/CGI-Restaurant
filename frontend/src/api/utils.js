export const getTodayDateString = () => new Date().toISOString().split('T')[0];

export const generateTimeSlots = (startHour = 10, endHour = 22) => {
    const slots = [];
    for (let i = startHour; i <= endHour; i++) {
        slots.push(`${String(i).padStart(2, '0')}:00`);
    }
    return slots;
};