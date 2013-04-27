package com.danielwellman.birthdaygreetings.domain;

import java.util.Collection;

public class BirthdayService {
    private final Notifier notifier;
    private final PersonRegistry personRegistry;

    public BirthdayService(Notifier notifier, PersonRegistry personRegistry) {
        this.notifier = notifier;
        this.personRegistry = personRegistry;
    }

    public void sendGreetings(Date today) {
        Collection<Person> people = personRegistry.birthdaysOn(today.monthAndDate());
        for (Person person : people) {
            notifier.notify(person);
        }
    }
}
