# Usage

Instantiate an instance of _[what3words.com][]_ with [Feign][feign]

```java
api = Feign.builder()
        .client(new Http2Client())
        .decoder(new What3WordsDecoder())
        .errorDecoder(new What3WordsErrorDecoder())
        .requestInterceptor(new KeyInterceptor("abc-api-key"))
        .target(What3Words.class, "https://api.what3words.com/");
```

This is a minimal configuration and `KeyInterceptor` is optional:
you can put an API key in request data.

## Convert coordinates to 3 word address

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

The language is optional: if you do not add
then _what3words_ returns English words _///spring.tops.issued_.

## Convert 3 word address to coordinates

```java
CoordinatesRequest coordinatesRequest = CoordinatesRequest.builder()
        .words("filled.count.soap"))
        .build();

Coordinates coordinates = api.convertToCoordinates(coordinatesRequest).getCoordinates();
```

It returns [well known coordinates][filled.count.soap] of the _what3words_'s office.

## Get available languages

```java
Collection<Language> languages = api.availableLanguages();
```

or with explicit API key

```java
Collection<Language> languages = api.availableLanguages("xyz-api-key");
```

[what3words.com]: https://what3words.com/ "It’s the easiest way to find and share exact locations."
[feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier."
[filled.count.soap]: https://twitter.com/what3words/status/1005118966132551681
