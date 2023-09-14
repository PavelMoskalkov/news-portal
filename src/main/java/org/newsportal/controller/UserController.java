package org.newsportal.controller;

import lombok.RequiredArgsConstructor;
import org.newsportal.service.UserService;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news-portal")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
    @PostMapping("/users")
    public ResponseEntity<Long> createUsers(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.add(userDto));
    }
    @DeleteMapping("/users/{id}")
    public void deleteUsers(@PathVariable("id") Long id) {
        userService.removeById(id);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateArticle(@PathVariable("id") Long id, @RequestBody UserDto request) {
        return ResponseEntity.ok(userService.changeById(id, request));
    }


}
