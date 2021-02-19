# _what3words_ API wrapper

Unofficial Java wrapper for _[what3words.com][]_ API
to convert 3 word addresses to coordinates and vice versa.

## Installation

### Repository

The package is not published to Maven Central yet. Use bot-by's GitLab repository instead, please:

```xml
<repositories>
  <repository>
    <id>bot-by-maven</id>
    <url>https://gitlab.com/api/v4/groups/7239110/-/packages/maven</url>
  </repository>
</repositories>
```
or
```gradle
repositories {
    maven {
        url "https://gitlab.com/api/v4/groups/7239110/-/packages/maven"
        name "bot-by"
    }
}
```

### Dependency

#### Maven

```xml
<dependency>
  <groupId>uk.bot-by.3wa</groupId>
  <artifactId>what3words-api</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
or
```gradle
dependencies {
    compile('uk.bot-by.3wa:what3words-api:1.0.0-SNAPSHOT')

    // Other dependencies your app might use
}
```

## Usage

TODO: Write usage instructions

## Contributing

Please read [CONTRIBUTING](CONTRIBUTING.md).

## History

See [CHANGELOG](CHANGELOG.md)

## License

Copyright 2021 Witalij Berdinskich

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[Apache License v2.0](LICENSE)  
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[what3words.com]: https://what3words.com/ "It’s the easiest way to find and share exact locations."
