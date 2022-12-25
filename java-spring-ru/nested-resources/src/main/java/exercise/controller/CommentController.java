package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN

    @GetMapping(path = "/{postId}/comments")
//  вывод всех комментариев для конкретного поста. Должны выводится только комментарии, принадлежащие посту.
    public Iterable<Comment> getAllCommentsByPostId(@PathVariable long postId) {
        return commentRepository.findAllByPostId(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comments for post " + postId + "not found"));
    }

    @GetMapping(path = "/{postId}/comments/{commentId}")
//  вывод конкретного комментария для поста. Должны выводится только комментарий, принадлежащие посту. Если такой комментарий не существует, должен вернуться ответ с кодом 404.
    public Comment getCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment " + commentId + "for post " + postId + "not found"));
    }

    @PostMapping(path = "/{postId}/comments")
//  создание нового комментария для поста. Должны выводится только комментарий, принадлежащие посту.
    public Comment createCommentForPost(@PathVariable long postId, @RequestBody Comment comment) {
        comment.setPost(postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post " + postId + "not found")));
        return commentRepository.save(comment);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
//  редактирование конкретного комментария поста. Если такой комментарий не существует, должен вернуться ответ с кодом 404.
    public Comment updateCommentForPost(@PathVariable long postId, @PathVariable long commentId, @RequestBody Comment comment) {
        commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment " + commentId + "for post " + postId + "not found"));
        comment.setPost(postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post " + postId + "not found")));
        comment.setId(commentId);
        return commentRepository.save(comment);
    }

    @DeleteMapping(path = "/{postId}/comments/{commentId}")
//  удаление конкретного комментария у поста. Если такой комментарий не существует, должен вернуться ответ с кодом 404.
    public void deleteCommentForPost (@PathVariable long postId, @PathVariable long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment " + commentId + "for post " + postId + "not found"));
        commentRepository.delete(comment);
    }

   // END
}
