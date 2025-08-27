 Build a health app which have the following features:

Allow logging sleep, exercises manually in app.
Sync sleep, exercise logs from Google Health.
Detect, highlight, and allow solving any sleep, exercise log conflicts (because of data from different sources such as Samsung Health, Garmin, manual inputs)

Using MVVM Design Architecture as follow:

com.roman.thehealthone
│
├─ MainActivity.kt                # Entry point, sets up NavController, ViewModel, and AppNav
├─ NavRoute.kt                    # Bottom navigation routes
├─ ui
│   ├─ theme                     # MaterialTheme, colors, typography
│   │   ├─ Color.kt
│   │   ├─ Typography.kt
│   │   └─ TheHealthOneTheme.kt
│   │
│   ├─ nav
│   │   └─ AppNav.kt             # NavHost, bottom navigation bar
│   │
│   ├─ components                # Reusable UI components / input forms
│   │   ├─ LogInputField.kt      # For manual sleep input
│   │   └─ ExerciseInputForm.kt  # For manual exercise input
│   │
│   └─ screens
│       └─ tabs
│           ├─ SleepScreen.kt
│           ├─ ExerciseScreen.kt
│           ├─ LogsScreen.kt          # Tabbed Sleep + Exercise
│           ├─ SyncScreen.kt          # Google Health sync
│           ├─ ConflictsScreen.kt     # Show detected conflicts
│           ├─ ResolutionsScreen.kt   # Resolve conflicts
│           └─ ManualInputScreen.kt   # Manual logging (Sleep + Exercise)
│
├─ data
│   ├─ model
│   │   ├─ SleepLog.kt
│   │   ├─ ExerciseLog.kt
│   │   └─ LogConflict.kt
│   │
│   └─ db
│       ├─ AppDatabase.kt
│       ├─ SleepDao.kt
│       └─ ExerciseDao.kt
│
├─ repository
│   ├─ HealthRepository.kt         # CRUD operations for Sleep + Exercise
│   └─ SyncRepository.kt           # Sync with Google Health + detect conflicts
│
├─ util
│   └─ DateTimeUtils.kt            # Format epoch ms → readable string
│
└─ viewmodel
    └─ HealthViewModel.kt          # Exposes sleepLogs, exerciseLogs, conflicts, addSleep/addExercise, detect/resolve conflicts

Key Guidelines:

1. Screens:

LogsScreen → tabbed Sleep + Exercise

SyncScreen → sync Google Health

ConflictsScreen → list conflicts

ResolutionsScreen → resolve conflicts

ManualInputScreen → manual sleep/exercise input

2. ViewModel:

Single HealthViewModel exposes StateFlow for sleep, exercise, conflicts

Handles addSleep, addExercise, detectConflicts, resolveConflict

3. Repository:

HealthRepository → CRUD for local DB

SyncRepository → Google Health sync, conflict detection

4. Components:

LogInputField → manual sleep input

ExerciseInputForm → manual exercise input

5. Navigation:

AppNav.kt → single NavHost with bottom navigation

Bottom nav highlights current tab and navigates via NavHostController

6. Utilities:

DateTimeUtils for formatting epoch ms timestamps

This structure ensures:

Clear separation of UI, data, and logic

Easy addition of new features (e.g., Heart Rate, Steps)

Maintainable scalable architecture

Health App File & Data Flow Map
MainActivity.kt
│
├─ Initializes NavController + HealthViewModel
│
└─ AppNav.kt (NavHostController)
    ├─ BottomNavigationBar
    │
    └─ NavHost
        ├─ SyncScreen.kt            <-- Sync with Google Health
        ├─ LogsScreen.kt            <-- Tabbed Sleep + Exercise
        │   ├─ SleepScreen.kt       <-- displays sleepLogs + LogInputField
        │   └─ ExerciseScreen.kt   <-- displays exerciseLogs + ExerciseInputForm
        ├─ ConflictsScreen.kt       <-- shows conflicts from HealthViewModel.conflicts
        ├─ ResolutionsScreen.kt     <-- resolve conflicts
        └─ ManualInputScreen.kt     <-- manual input of sleep/exercise logs


HealthViewModel.kt (Logic Layer)
HealthViewModel
├─ StateFlows
│   ├─ sleepLogs: StateFlow<List<SleepLog>>
│   ├─ exerciseLogs: StateFlow<List<ExerciseLog>>
│   └─ conflicts: StateFlow<List<LogConflict>>
│
├─ Functions
│   ├─ addSleep(start, end)
│   ├─ addExercise(type, duration, start, end, calories?, distance?)
│   ├─ detectConflicts(sleepLogs, exerciseLogs)
│   ├─ resolveConflict(resolvedLog)
│   └─ syncWithGoogleHealth()
│
└─ Calls repositories
    ├─ HealthRepository -> DB
    └─ SyncRepository   -> Google Health, detect conflicts

Repository Layer
HealthRepository
├─ sleepDao(): SleepDao
├─ exerciseDao(): ExerciseDao
├─ getAllSleep()
├─ addSleep(SleepLog)
├─ getAllExercise()
└─ addExercise(ExerciseLog)

SyncRepository
├─ detectConflicts(sleepLogs, exerciseLogs) -> returns List<LogConflict>
└─ syncGoogleHealth() -> fetch & merge sleep/exercise logs from Google Health

Database Layer
AppDatabase
├─ SleepDao
│   ├─ insert(sleepLog)
│   └─ queryAll(): List<SleepLog>
└─ ExerciseDao
    ├─ insert(exerciseLog)
    └─ queryAll(): List<ExerciseLog>

Data Models
├─ SleepLog(id, startTime, endTime, source, isDeleted)
├─ ExerciseLog(id, startTime, endTime, type, durationMinutes, calories?, distance?, source, isDeleted)
└─ LogConflict (sealed class)
    ├─ SleepConflict(log1, log2)
    └─ ExerciseConflict(log1, log2)

UI Components
LogInputField.kt          <-- Manual sleep input form
ExerciseInputForm.kt      <-- Manual exercise input form
DateTimeUtils.kt          <-- Utility to format epoch -> human-readable

Data Flow

1. User interacts with UI (SleepScreen / ExerciseScreen / ManualInputScreen).

2. UI calls HealthViewModel functions (addSleep, addExercise).

3. ViewModel updates StateFlow and calls HealthRepository for DB persistence.

4. SyncRepository is used for Google Health sync & conflict detection.

5. Conflicts are shown in ConflictsScreen → can be resolved → ViewModel updates DB.

6. UI observes StateFlow → automatically recomposes with updated data.
