# UmmaApp: University Management Mobile Application

Android app written in Kotlin for Umma University, built for the Mobile Application Development CAT II.

## Group members
- Mandeq Ahmed, BSCS/2024/52805
- Fathi Ali, BSCS/2024/52130

## Features
- Secure registration and login (passwords stored as hashes)
- View enrolled courses
- Weekly timetable
- Examination results with grades
- University announcements

## Tech stack
- Kotlin and Android Studio
- Room (SQLite) for local storage
- Kotlin coroutines and LiveData

## How to run
1. Open the project in Android Studio.
2. Run it on an emulator (Medium Phone, API 37) or an Android phone.
3. Tap Register, create an account, then log in.

## Database
Six tables: users, courses, enrollments, results, timetable, announcements.

## Limitations
Fees, library, e-learning, campus services, admissions and full lecturer and admin dashboards are not yet built.
