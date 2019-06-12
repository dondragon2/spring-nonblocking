### spring-nonblocking
A simple framework to enable non-blocking on spring rest controllers and services

**Installation:**

---

**Maven**
```xml
<dependency>
    <groupId>io.github.dondragon2</groupId>
    <artifactId>spring-nonblocking</artifactId>
    <version>0.1.2</version>
</dependency>
```

**Gradle**
```groovy
compile group: 'io.github.dondragon2', name: 'spring-nonblocking', version: '0.1.2'
```

**Usage:**

---
There are 2 ways of using spring-nonblocking

**Annotation (*recommended*)**
```java
@RestController
public class ExampleResource {
    
    @Autowired
    private ExampleService exampleService;

    /*
        Note: return type must be "Object" to avoid ClassCastException
    */
    @NonBlocking
    @GetMapping("/greetings-non-blocking")
    public Object greeting() {
        return this.exampleService.longProcess();     	        
    }
    

    @GetMapping("/greetings-blocking")
    public String greetingBlocking() {   
        return this.exampleService.longProcess();   	        
    }
}

@Service
public class ExampleService {
    public String longProcess() {
        //simulate long running process
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
        }
        return "Hello World"; 
    }
}
```

**Inheritance**
```java
@RestController
public class ExampleResource {
    
    @Autowired
    private ExampleService exampleService;

    @GetMapping("/greetings-non-blocking")
    public Observable greeting() {
        return this.exampleService.nonBlockingProcess();     	        
    }
    
    @GetMapping("/greetings-blocking")
    public String greetingBlocking() {   
        return this.exampleService.longProcess();   	        
    }
}

@Service
public class ExampleService extends NonBlockingService {
    public Observable nonBlockingProcess() {
        return this.process(() -> longProcess());
    }
    
    public String longProcess() {
        //simulate long running process
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
        }
        return "Hello World";
    }
}
```
