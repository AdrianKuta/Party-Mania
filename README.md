# Party Mania

Party Mania is an Android application with party games. Initially, it only contains the Truth or
Dare game, but eventually there will be more games added.

## CI/CD Setup

This project uses GitHub Actions for continuous integration and deployment:

### Continuous Integration

Pull requests to `main` and `develop` branches trigger the CI workflow, which:

- Runs static code analysis with Detekt
- Builds a debug APK
- Runs unit tests
- Comments on the PR with a link to download the debug APK

### Continuous Deployment

When a new version is ready for release:

1. Create a tag with a 'v' prefix (e.g., `v1.0.0`)
2. Push the tag to GitHub
3. The release workflow automatically:
    - Creates a GitHub Release
    - Builds release APK and AAB bundle
    - Attaches these artifacts to the Release
    - Comments on the associated PR with download links

### Branch Management

When changes are merged to `main`, the `develop` branch is automatically rebased onto `main` to keep
it up-to-date.

For more detailed information about the CI/CD setup, see
the [workflows documentation](.github/workflows/README.md).

## Development Setup

(Add development setup instructions here)

## License

(Add license information here)
