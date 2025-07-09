
package com.example.style_store_be_adminSP.controller;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import com.example.style_store_be_adminSP.service.impl.ChatLieuServiceImplAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-lieu")
@CrossOrigin("*")
public class ChatLieuControllerAdm {
    @Autowired
    private ChatLieuServiceImplAdm chatLieuService;

    @GetMapping("/all")
    public ResponseEntity<Page<ChatLieuAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ChatLieuAdm> chatLieuPage = chatLieuService.getAll(page, size);
        return ResponseEntity.ok(chatLieuPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ChatLieuAdm>> searchByName(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ChatLieuAdm> chatLieuPage = chatLieuService.searchByName(ten, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @GetMapping("/search-by-name-or-code")
    public ResponseEntity<Page<ChatLieuAdm>> searchByNameOrCode(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ChatLieuAdm> chatLieuPage = chatLieuService.searchByNameOrCode(keyword, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ChatLieuAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ChatLieuAdm> chatLieuPage = chatLieuService.getActive(page, size);
        return ResponseEntity.ok(chatLieuPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<ChatLieuAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ChatLieuAdm> chatLieuPage = chatLieuService.getDeleted(page, size);
        return ResponseEntity.ok(chatLieuPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatLieuAdm> getOne(@PathVariable Long id) {
        ChatLieuAdm chatLieu = chatLieuService.getOne(id);
        return chatLieu != null ? ResponseEntity.ok(chatLieu) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ChatLieuAdm> create(@RequestBody ChatLieuAdm chatLieu) {
        try {
            ChatLieuAdm createdChatLieu = chatLieuService.add(chatLieu);
            return ResponseEntity.status(201).body(createdChatLieu); // Trả về đối tượng vừa tạo
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatLieuAdm> update(@PathVariable Long id, @RequestBody ChatLieuAdm chatLieu) {
        try {
            chatLieu.setId(id); // Gán ID từ path variable
            chatLieuService.update(chatLieu);
            return ResponseEntity.ok(chatLieuService.getOne(id)); // Trả về đối tượng sau khi cập nhật
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            chatLieuService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
