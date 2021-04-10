# Unofficial Java wrapper for _[what3words.com][]_ API to convert 3 word addresses to coordinates and vice versa

I was fascinated by the idea of three-word addresses,
which is the basis of the site _[what3words.com][]_.
But I want to realise the library to another way
than [official package][w3w-java-wrapper] provides.

## Acquire

The package is not published to Maven Central yet.
Use bot-by's GitLab repository instead, please:

```language-xml
<repositories>
  <repository>
    <id>bot-by-maven</id>
    <url>https://gitlab.com/api/v4/groups/7239110/-/packages/maven</url>
  </repository>
</repositories>
```

Please add dependency to your project:

```language-xml
<dependency>
  <groupId>uk.bot-by.3wa</groupId>
  <artifactId>what3words-api</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Usage

Instantiate an instance of What3Words with [Feign][feign]

```language-java
api = Feign.builder()
           .client(new Http2Client())
           .decoder(new What3WordsDecoder())
           .errorDecoder(new What3WordsErrorDecoder())
           .requestInterceptor(new KeyInterceptor("abc-api-key"))
           .target(What3Words.class, "https://api.what3words.com/");
```

This is a minimal configuration and `KeyInterceptor` is optional:
you can put an API key in request data.

### Convert coordinates to 3 word address

```language-java
WordsRequest wordsRequest = WordsRequest.builder()
                                        .coordinates(51.381051d, -2.359591d)
                                        .language(Language.builder()
                                                          .code("uk")
                                                          .build())
                                        .build();

Words words = api.convertToAddress(wordsRequest).getWords();
```

It converts coordinates of Roman Baths
to Ukrainian words _///зрання.поїздка.зрізаний_.

The language is optional: if you do not add it
then _what3words_ returns English words _///spring.tops.issued_.

### Convert 3 word address to coordinates

```language-java
CoordinatesRequest coordinatesRequest = CoordinatesRequest.builder()
                                                          .words("filled.count.soap")
                                                          .build();

Coordinates coordinates = api.convertToCoordinates(coordinatesRequest).getCoordinates();
```

It returns [well known coordinates][filled.count.soap] of the _what3words_'s office.

### Get available languages

```language-java
Collection<Language> languages = api.availableLanguages();
```

or with explicit API key

```language-java
Collection<Language> languages = api.availableLanguages("xyz-api-key");
```

## Contributing

Please read [Contributing](contributing.md).

## History

See [Changelog](changelog.md)

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
[w3w-java-wrapper]: https://github.com/what3words/w3w-java-wrapper "Java library for what3words REST API."
[feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier."
[filled.count.soap]: https://twitter.com/what3words/status/1005118966132551681
