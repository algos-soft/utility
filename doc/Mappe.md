# ⚙️ Mappe

## Possibilità
1. `Map.of()`
2. `Map.ofEntries()`
3. `HashMap<>()`

### 1) `Map.of()`

      private static final Map<String, String> STATIC_MAP = Map.of(
      "chiave1", "valore1",
      "chiave2", "valore2"
      );

#### Vantaggi
- Crea una mappa immutabile
- Sintassi molto concisa
- Ottimizzato per performance
- Thread-safe per definizione (immutabile)
#### Limitazioni
- Massimo 10 coppie chiave-valore
- Non accetta valori `null`
- Non può essere modificata dopo la creazione
#### Consigliata
- Per mappe piccole e immutabili (fino a 10 entries)
- Quando sei sicuro che i valori non saranno mai `null`
- Per costanti e valori statici immutabili

### 2) `Map.ofEntries()`

    private static final Map<String, String> STATIC_MAP = Map.ofEntries(
    Map.entry("chiave1", "valore1"),
    Map.entry("chiave2", "valore2"),
    Map.entry("chiave3", "valore3")
    );

#### Vantaggi
- Crea una mappa immutabile
- Nessun limite al numero di entities
- Thread-safe (immutabile)
- Più leggibile con molte entries
#### Limitazioni
- Non accetta valori `null`
- Sintassi più verbosa di `Map.of()`
- Non può essere modificata dopo la creazione
#### Consigliata
- Per mappe immutabili con più di 10 entries
- Quando la leggibilità è importante
- Per costanti e valori statici immutabili di grandi dimensioni


### 3) `new HashMap<>()`

    private Map<String, String> dynamicMap = new HashMap<>();
    map.put("chiave1", "valore1");
    map.put("chiave2", "valore2");

o con double-brace initialization (sconsigliato):

    private Map<String, String> dynamicMap = new HashMap<>() {{
    put("chiave1", "valore1");
    put("chiave2", "valore2");
    }};

#### Vantaggi
- Mappa mutabile
- Accetta valori `null`
- Nessun limite di dimensione
- Può essere modificata dopo la creazione
#### Svantaggi
- Non thread-safe di default 
- Richiede più memoria rispetto alle versioni immutabili
- La double-brace initialization crea una classe anonima (memory leak potenziale)
#### Uso consigliato
- Quando hai bisogno di una mappa mutabile
- Quando devi supportare valori `null`
- Quando la mappa verrà modificata durante l'esecuzione

## Best Practices
#### 1. Per costanti e configurazioni statiche:
- Usa `Map.of()` se hai ≤ 10 entries

       private static final Map<String, String> STATIC_MAP = Map.of("key1", "value1");

- Usa `Map.ofEntries()` se hai > 10 entries

      private static final Map<String, String> STATIC_MAP = Map.ofEntries("key1", "value1");
      Map.entry("chiave1", "valore1"),
      Map.entry("chiave2", "valore2"),
      ...
      );

#### 2. Per Mappe che devono essere modificate:
- Usa `new HashMap<>()`

      private Map<String, String> dynamicMap = new HashMap<>();

#### 3. Per collezioni condivise tra thread:
- Usa le versioni immutabili (`Map.of()` o `Map.ofEntries()`)
- O usa `Collections.synchronizedMap()` se deve essere mutabile

#### 4. Evita la double-brace initialization:
    // DA EVITARE
    private Map<String, String> dynamicMap = new HashMap<>() {{
       put("key", "value");
    }};
   
    // PREFERIRE
    private Map<String, String> dynamicMap = new HashMap<>()
    map.put("key", "value");

#### 5. Per l'inizializzazione con valori predefiniti:
    // Se i valori sono noti a compile-time e immutabili
    Map<String, String> constants = Map.of("key1", "value1");
   
    // Se i valori sono dinamici o mutabili
    Map<String, String> dynamic = new HashMap<>();
    dynamic.put("key1", getValue());

## Conclusione
- Usa `Map.of()` o `Map.ofEntries()` per collezioni immutabili (preferisci `Map.of()` per piccole mappe)
- Usa `new HashMap<>()` per collezioni mutabili
- Evita la double-brace initialization
- Scegli l'immutabilità quando possibile per maggiore sicurezza e thread-safety
