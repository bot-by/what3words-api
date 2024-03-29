# Unofficial Java wrapper for _[what3words.com][]_ API to convert 3 word addresses into coordinates and vice versa

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/09b4a1319ea84d3aa16732bd09e70523)](https://app.codacy.com/gl/bot-by/what3words-api/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/09b4a1319ea84d3aa16732bd09e70523)](https://app.codacy.com/gl/bot-by/what3words-api/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
[![Java Version](https://img.shields.io/static/v1?label=java&message=11&color=blue&logo=java&logoColor=E23D28)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

I was fascinated by the idea of three-word addresses,
which is the basis of the site _[what3words.com][]_.
But I want to realise the library to another way
than [official package][w3w-java-wrapper] provides.

## Acquire

Please add dependency to your project:

```xml
<dependency>
  <groupId>uk.bot-by.3wa</groupId>
  <artifactId>what3words-api</artifactId>
  <version><!-- check releases page --></version>
</dependency>
```

## Usage

Instantiate an instance of What3Words with [Feign][feign]

```java
api = Feign.builder()
           .client(new Http2Client())
           .decoder(new What3WordsDecoder())
           .errorDecoder(new What3WordsErrorDecoder())
           .requestInterceptor(new KeyInterceptor("qwerty-api-key"))
           .target(What3Words.class, What3Words.W3W_API);
```

This is a minimal configuration and `KeyInterceptor` is optional:
you can put an API key in request data.

### Convert coordinates to 3 word address

```java
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

### Convert 3 word address into coordinates

```java
CoordinatesRequest coordinatesRequest = CoordinatesRequest.builder()
                                                          .words("filled.count.soap")
                                                          .build();

Coordinates coordinates = api.convertToCoordinates(coordinatesRequest).getCoordinates();
```

It returns [well known coordinates][filled.count.soap] of the _what3words_'s office.

### Get available languages

```java
Collection<Language> languages = api.availableLanguages();
```

or with explicit API key

```java
Collection<Language> languages = api.availableLanguages("xyz-api-key");
```

## Contributing

Please read [Contributing](contributing.md).

## History

See [Changelog](changelog.md)

## License

Copyright 2021,2022 Witalij Berdinskich

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
