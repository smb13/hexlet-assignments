package exercise.controller;

import exercise.model.User;
import exercise.repository.UserRepository;
import exercise.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

// Импортируем аннотации, которые помогут задокументировать API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // Аннотация отмечает метод контроллера как операцию
    // И определяет краткую информацию об этой рперации
    @Operation(summary = "Get list of all users")
    // Аннотация определяет ответ, который может быть получен (код и описание)
    @ApiResponse(responseCode = "200", description = "List of all users")
    @GetMapping(path = "")
    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Operation(summary = "Get specific user by his id")
    // Контейнер для аннотаций @ApiResponse
    // Используется в случае, если нужно указать более одного ответа
    @ApiResponses(value = {
        // Указываем, что операция вернет ответ с кодом 200 в случае успешного выполнения
        @ApiResponse(responseCode = "200", description = "User found"),
        // И ответ с кодом 404 в случе, если пользователь не найден
        @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @GetMapping(path = "/{id}")
    public User getUser(
        // Аннотация отмечает параметр метода, как параметр для операции
        // И определяет его описание
        @Parameter(description = "Id of user to be found")
        @PathVariable long id) {

        return this.userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(
        @Parameter(description = "User data to save")
        @RequestBody User user) {
        return userRepository.save(user);
    }

    @Operation(summary = "Delete user by his id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @DeleteMapping(path = "/{id}")
    public void deleteUser(
        @Parameter(description = "Id of user to be deleted")
        @PathVariable long id) {
        // Проверяем, существует ли пользователь с таким id
        if (!userRepository.existsById(id)) {
            // Если не существует, возвращаем код ответа 404
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    // BEGIN
    @Operation(summary = "Patch user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User patched"),
            @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @PatchMapping(path = "/{id}")
    public void patcheUser(
            @Parameter(description = "Id of user to be patched")
            @PathVariable long id,
            @Parameter(description = "User data to patch")
            @RequestBody User user) {
        // Проверяем, существует ли пользователь с таким id
        if (!userRepository.existsById(id)) {
            // Если не существует, возвращаем код ответа 404
            throw new UserNotFoundException(id);
        }
        user.setId(id);
        userRepository.save(user);
    }
    // END
}
