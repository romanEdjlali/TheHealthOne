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
