[![Java CI](https://github.com/AY2526S2-CS2103-F11-1/tp/actions/workflows/gradle.yml/badge.svg)] [![codecov](https://codecov.io/gh/AY2526S2-CS2103-F11-1/tp/graph/badge.svg?token=CR2V7F145N)](https://codecov.io/gh/AY2526S2-CS2103-F11-1/tp)

![Ui](docs/images/Ui.png)

## Project Origin
>This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

---

# CareSync

**CareSync** is a desktop application designed for **Social Workers in Singapore** to manage client contacts and home visit schedules efficiently. It is optimized for those who prefer typing (Command Line Interface) but still want the benefits of a Graphical User Interface (GUI).

---

## Why CareSync?

Social workers like **Alice** (a community social service officer) often handle high volumes of low-income households and elderly clients. CareSync addresses these specific needs by providing:

* **Targeted Organization:** Categorize contacts by roles such as `Client`, `Caregiver`, `MSF Agency`, or `Hospital`.
* **Visit Tracking:** Quick access to visit history and scheduling without needing an external calendar.
* **Efficient Workflows:** Optimized for users with fast typing speeds, supports bulk operations (e.g., tagging multiple contacts at once), and provides a "Today View" to facilitate handling daily tasks.

---

## Key Features

### Visit & Schedule Management
* **Visit Tracking:** Set specific dates, times, and durations for home visits.
* **Status Indicators:** Mark visits as *Planned*, *Completed*, or *Cancelled*. Completed records are locked to ensure accountability.
* **Visit Notes:** Add specific notes to each contact to record the purpose and outcome of every encounter.

### Advanced Categorization
* **Case ID System:** Link family members and related service providers under a unique Case ID for holistic case management.
* **Role Tagging:** Use flexible tags to differentiate between clients, caregivers, and government agencies.
* **Dynamic Filtering:** Filter by tags or search by name/phone to find information in seconds.

### Optimized for Power Users
* **CLI-First Design:** Accomplish tasks faster than a GUI with specialized commands.
* **Command Aliases & History:** Create shorthand for frequent actions and reuse past commands to save time.
* **Undo & Redo:** Quickly reverse accidental changes to your database.

---

## Technical Requirement
* **Platform:** Cross-platform support (Windows, macOS, Linux) via **Java 17**.
