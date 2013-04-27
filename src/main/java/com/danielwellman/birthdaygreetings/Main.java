package com.danielwellman.birthdaygreetings;

import com.danielwellman.birthdaygreetings.adapters.notifiers.inmemory.InMemoryPostOffice;
import com.danielwellman.birthdaygreetings.adapters.registry.filesystem.FileSystemPeopleSource;
import com.danielwellman.birthdaygreetings.adapters.registry.filesystem.InMemoryPersonRegistry;
import com.danielwellman.birthdaygreetings.domain.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private final TodaySource calendar;
    private final PostOffice postOffice;
    private final Path path;

    public Main(TodaySource calendar, PostOffice postOffice, Path path) {
        this.calendar = calendar;
        this.postOffice = postOffice;
        this.path = path;
    }

    public void run() {
        BirthdayService birthdayService = new BirthdayService(new EmailNotifier(this.postOffice),
                new InMemoryPersonRegistry(new FileSystemPeopleSource(this.path)),
                new ObserveLeapYearBirthdaysEarlyCalculator());
        birthdayService.sendGreetings(this.calendar.today());
    }

    public static void main(String[] args) {
        // FUTURE This could accept an argument for the filename to use
        new Main(new SystemTimeTodaySource(), new InMemoryPostOffice(), Paths.get("birthdays.txt")).run();
    }

}