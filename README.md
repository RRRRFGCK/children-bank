# Children's savings desktop application

A JavaFX coursework application with separate child, parent and administrator interfaces, local account records and savings-task workflows.

## Source

- `MainApplication.java`, `Main.java`: application entry points.
- `ChildController.java`, `ParentController.java`, `AdminController.java`: role-specific interfaces.
- `*-view.fxml`: JavaFX views.
- `*Data.java`, `FileUtil.java`: local JSON storage.
- `pom.xml`: Java 21, JavaFX 21 and Maven dependency settings.

## Status

This repository preserves an older coursework source snapshot. The Java files and FXML/resources are stored flat, while their package declarations and resource lookups expect a nested layout. Reconstruct that layout in a working copy before attempting a source build; a clean Maven build is not currently verified.

The repository also contains historical JAR/ZIP exports. Their contents and current execution compatibility have not been verified in this documentation update.

This is a local educational application, not banking software or a service for real money.
