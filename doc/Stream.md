# ⚙️ Stream Java


## 🚀 Mappe per i tests

```java

        private static final Map<String, String> STATIC_MAP = Map.of(
                "chiave1", "valore1",
                "chiave2", "valore2"
        );
```

```java

        static Stream<Arguments> cronPatterns() {
            return STATIC_MAP.entrySet()
                    .stream()
                    .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
        }
```

```java

        protected static Stream<Arguments> CRON_VALIDE() {
            return Stream.of(
                    Arguments.of("Abba-El I", 14),
                    Arguments.of("Abba-El I", 14),
                    Arguments.of("Abba-El I", 14)
            );
        }
```

