# data-structures-algorithms

Projeto didático de estruturas de dados em Java: reimplementação de `List`, `Stack`, `Queue`
e ordenação, cada uma com duas estratégias de armazenamento — array redimensionável e lista
ligada — para comparar o comportamento e o custo de cada operação na prática.

## Como rodar

Requer Java 25. Não há Maven Wrapper neste projeto; use uma instalação local do Maven (ou a
integração do Maven na sua IDE).

```bash
mvn test                          # roda os 47 testes JUnit 5
mvn compile exec:java -Dexec.mainClass=com.lucas.Main   # ou rode Main pela IDE
```

`Main.java` é uma demonstração executável: instancia cada estrutura e imprime o resultado de
cada operação no console. Não substitui os testes — serve para ler o comportamento sem precisar
rodar um debugger.

## Estrutura do código

```
com.lucas.util      -> interfaces: List, Stack, Queue, SortableList (o contrato)
com.lucas.array     -> ArrayList, ArrayStack, ArrayQueue, SortableArrayList (array redimensionável)
com.lucas.linked    -> LinkedList, LinkedStack, LinkedQueue (nós ligados)
```

Cada interface documenta o contrato (o que cada método garante, quando lança exceção) e cada
classe concreta documenta a complexidade Big-O de cada operação no seu próprio Javadoc — a
complexidade depende da estratégia de armazenamento, não faz sentido documentá-la na interface.

> Nota de nomenclatura: `List`, `Stack`, `Queue`, `ArrayList` e `LinkedList` têm o mesmo nome das
> classes de `java.util`. É proposital — o objetivo é comparar com as implementações padrão do
> Java — mas significa que `import java.util.*` nesse código geraria ambiguidade. Os imports
> aqui são sempre explícitos.

## Complexidade por operação

| Operação          | `ArrayList` | `LinkedList` |
|-------------------|-------------|--------------|
| `get(index)`      | O(1)        | O(n)         |
| `position(elem)`  | O(n)        | O(n)         |
| `add(elem)`       | O(1) amort. | O(1)         |
| `addByIndex(0,·)` | O(n)        | O(1)         |
| `addByIndex(i,·)` | O(n)        | O(n)         |
| `removeByIndex`   | O(n)        | O(1) na cabeça, O(n) no resto |
| `replace`         | O(1)        | O(n)         |

| Operação | `ArrayStack` | `LinkedStack` |
|----------|--------------|---------------|
| `push`   | O(1) amort.  | O(1)          |
| `pop`    | O(1)         | O(n)          |
| `peek`   | O(1)         | O(1)          |

`ArrayStack` guarda o topo no último slot em uso (`array[size-1]`), então `push`/`pop`/`peek`
nunca tocam o início do array. `LinkedStack` guarda o topo no ponteiro `last`: `push` e `peek`
são O(1), mas `pop` precisa percorrer a lista inteira a partir da cabeça para achar o nó anterior
ao `last`, porque a lista é ligada apenas para frente (sem ponteiro `previous`). Esse é o ponto
mais interessante da comparação — a implementação em array é estritamente melhor aqui.

| Operação | `ArrayQueue` | `LinkedQueue` |
|----------|--------------|---------------|
| `push`   | O(1) amort.  | O(1)          |
| `pop`    | O(n)         | O(1)          |
| `peek`   | O(1)         | O(1)          |

`ArrayQueue` mantém a frente sempre em `array[0]`, de propósito ingênuo: cada `pop` desloca
todos os elementos restantes uma posição à esquerda. Um buffer circular (índice de cabeça móvel
em vez de deslocar) tornaria `pop` O(1) — é um próximo exercício natural a partir daqui.
`LinkedQueue`, com ponteiros `first`/`last`, já é O(1) em tudo.

`SortableArrayList` adiciona `sort()` via **selection sort**: O(n²) tempo, O(1) espaço extra,
não estável (dois elementos iguais podem trocar de ordem relativa entre si).

## Decisões de design

- **Contrato de índice inválido**: `get`, `addByIndex`, `removeByIndex` e `replace` sempre
  lançam `IndexOutOfBoundsException` quando o índice é inválido — em ambas as implementações.
  Já `position(elem)` retorna `-1` quando o elemento não é encontrado (não lança), porque não
  encontrar não é um erro de uso, é um resultado válido — segue a mesma convenção de
  `java.util.List#indexOf`. `remove(elem)` usa esse `-1` internamente para decidir entre remover
  de fato ou devolver `false`, sem nunca deixar um índice inválido chegar a `removeByIndex`.
- **Exceção em coleção vazia**: `Stack` sempre lança `EmptyStackException` em `pop`/`peek`
  vazios (convenção do próprio `java.util.Stack`); `Queue` sempre lança `NoSuchElementException`
  (convenção de `java.util.Queue#element()`/`remove()`). Antes desse ajuste, cada implementação
  lançava uma exceção diferente para a mesma condição — o ponto é que trocar `ArrayStack` por
  `LinkedStack` (ou `ArrayQueue` por `LinkedQueue`) não deveria mudar o que o código chamador
  precisa tratar.
- **Crescimento do array**: quando cheio, o array cresce em passos fixos de `+5` (crescimento
  aritmético), não dobrando de tamanho (crescimento geométrico, como `java.util.ArrayList`).
  Inserir `n` elementos custa O(n²) em cópias no total nesse esquema, contra O(n) amortizado com
  dobramento — deixado assim de propósito como ponto de comparação, não é bug.
- **`SortableArrayList` estende `ArrayList`**: reaproveita todo o armazenamento e as operações já
  testadas de `ArrayList`, adicionando só `sort()`. (A versão anterior implementava `List`
  diretamente com métodos stub que não guardavam nada — um esqueleto nunca terminado.)

## Testes

`src/test` usa um padrão de **testes de contrato**: `ListContractTest`, `StackContractTest` e
`QueueContractTest` são classes abstratas que declaram os testes uma única vez contra a
interface; cada implementação (`ArrayListTest`, `LinkedListTest`, ...) só fornece o método de
fábrica (`createList()`, `createStack()`, `createQueue()`). Isso garante que as duas
implementações de cada interface sejam testadas com exatamente as mesmas regras — se uma
implementação nova violar o contrato, o teste falha sem precisar duplicar nenhuma asserção.

```bash
mvn test
```

## Possíveis próximos passos

Fora do escopo atual, mas caminhos naturais para continuar explorando: árvore binária de busca,
hash table própria, heap/priority queue, um buffer circular para corrigir o `pop` O(n) de
`ArrayQueue`, e um `MergeSort`/`QuickSort` para comparar com o selection sort de
`SortableArrayList`.
