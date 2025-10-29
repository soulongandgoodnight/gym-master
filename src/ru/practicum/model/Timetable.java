package ru.practicum.model;

import ru.practicum.helper.CounterOfTrainings;

import java.util.*;

public class Timetable {

    private final Hashtable<DayOfWeek, SortedMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public Timetable(){
        var daysOfWeek = DayOfWeek.values();
        timetable = new Hashtable<>(daysOfWeek.length);
        for (var dayOfWeek: daysOfWeek) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        var dayOfWeek = trainingSession.getDayOfWeek();
        var timeOfDay = trainingSession.getTimeOfDay();
        if (!timeIsValid(timeOfDay)) {
            return;
        }

        if (!timetable.containsKey(dayOfWeek)) {
            return;
        }

        var sessionsOfDay = timetable.get(dayOfWeek);
        var sessionsOfDayAndTime = sessionsOfDay.get(timeOfDay);
        if (sessionsOfDayAndTime == null) {
            sessionsOfDayAndTime = new ArrayList<>();
            sessionsOfDay.put(timeOfDay, sessionsOfDayAndTime);
        }

        sessionsOfDayAndTime.add(trainingSession);
    }

    public SortedMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        var trainingSessionsByDay = timetable.get(dayOfWeek);
        return trainingSessionsByDay.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public Collection<CounterOfTrainings> getCountByCoaches(){
        var sessionsCountByCoaches = new HashMap<Coach, Integer>();
        for (var sessionByDay: timetable.values()) {
            for (var sessionsByTime: sessionByDay.values()) {
                for (TrainingSession sessionByTime : sessionsByTime) {
                    var coach = sessionByTime.getCoach();
                    var sessionsCount = sessionsCountByCoaches.getOrDefault(coach, 0);
                    sessionsCount++;
                    sessionsCountByCoaches.put(coach, sessionsCount);
                }
            }
        }

        List<CounterOfTrainings> list = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : sessionsCountByCoaches.entrySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(entry.getKey(), entry.getValue());
            list.add(counterOfTrainings);
        }

        Collections.sort(list);
        return list;
    }

    private boolean timeIsValid(TimeOfDay timeOfDay) {
        var hours = timeOfDay.getHours();
        var minutes = timeOfDay.getMinutes();
        return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
    }
}
