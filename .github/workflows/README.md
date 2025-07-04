# CI/CD Setup for Party Mania

This directory contains GitHub Actions workflows for continuous integration and deployment of the
Party Mania Android application with party games.

## Workflows

### 1. Android CI (`android-ci.yml`)

This workflow runs on pull requests targeting the `main` and `develop` branches.

It consists of four jobs:

- **Prepare**: Creates a dummy keystore.properties file for the build process
- **Lint**: Runs Detekt for static code analysis
- **Build**: Builds the debug APK and uploads it as an artifact
- **Test**: Runs unit tests and uploads test results
- **Comment**: Adds a comment to the PR with a link to download the debug APK

### 2. Android Release (`android-release.yml`)

This workflow runs when a tag with the pattern `v*` is pushed to the repository.

It consists of two jobs:

- **Release**:
   - Creates a GitHub Release for the tag
   - Sets up the keystore using secrets stored in the repository
   - Builds both the APK and AAB bundle for release
   - Uploads these artifacts to the GitHub Release
- **Comment**: Finds the PR associated with the tag and comments on it with links to download the
  release artifacts

### 3. Rebase Develop (`rebase-develop.yml`)

This workflow runs when a PR is merged into the `main` branch.

It automatically rebases the `develop` branch onto the `main` branch to ensure that `develop` always
contains the latest changes from `main`. If there are conflicts during the rebase, the workflow will
fail and require manual intervention.

## Setting Up Release Secrets

To enable the release workflow, you need to set up the following secrets in your GitHub repository:

1. `KEYSTORE_BASE64`: The base64-encoded keystore file
   ```bash
   base64 -i your-keystore.jks | tr -d '\n' | pbcopy  # On macOS
   base64 -w 0 your-keystore.jks | xclip -selection clipboard  # On Linux
   ```

2. `KEY_ALIAS`: The alias of the key in the keystore
3. `KEY_PASSWORD`: The password for the key
4. `STORE_PASSWORD`: The password for the keystore

## How to Create a Release

1. Update the version code and version name in `app/build.gradle.kts`
2. Commit and push your changes
3. Go to the GitHub repository page and click on "Releases"
4. Click on "Create a new release"
5. Enter a tag with a 'v' prefix (e.g., `v1.0.0`)
6. Fill in the release title and description
7. Click "Publish release"
8. The release workflow will automatically build the APK and attach it to the GitHub Release

## Best Practices

- Always run tests locally before pushing to the repository
- Keep the CI workflow fast by optimizing Gradle builds
- Use descriptive commit messages and PR titles to make the release notes more useful
