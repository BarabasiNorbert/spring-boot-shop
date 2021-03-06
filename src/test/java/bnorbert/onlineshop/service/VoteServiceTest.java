package bnorbert.onlineshop.service;

import bnorbert.onlineshop.domain.*;
import bnorbert.onlineshop.mapper.VoteMapper;
import bnorbert.onlineshop.repository.AnswerRepository;
import bnorbert.onlineshop.repository.CommentRepository;
import bnorbert.onlineshop.repository.VoteRepository;
import bnorbert.onlineshop.transfer.vote.VoteAnswersDto;
import bnorbert.onlineshop.transfer.vote.VoteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class VoteServiceTest {

    @Mock
    private VoteRepository mockVoteRepository;
    @Mock
    private AnswerRepository mockAnswerRepository;
    @Mock
    private CommentRepository mockCommentRepository;
    @Mock
    private UserService mockAuthService;
    @Mock
    private VoteMapper mockVoteMapper;

    private VoteService voteServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        voteServiceUnderTest = new VoteService(mockVoteRepository, mockAnswerRepository, mockCommentRepository, mockAuthService, mockVoteMapper);
    }

    @Test
    void testVoteComments() {
        final VoteDto request = new VoteDto(CommentsVoteType.HELPFUL, 1L);

        when(mockCommentRepository.findById(1L)).thenReturn(Optional.of(new Comment()));
        when(mockVoteRepository.findTopByCommentAndUserOrderByIdDesc(any(Comment.class), eq(new User()))).thenReturn(Optional.of(new Vote()));
        when(mockAuthService.getCurrentUser()).thenReturn(new User());
        when(mockVoteMapper.map(any(VoteDto.class), any(Comment.class), eq(new User()))).thenReturn(new Vote());
        when(mockVoteRepository.save(any(Vote.class))).thenReturn(new Vote());
        when(mockCommentRepository.save(any(Comment.class))).thenReturn(new Comment());

        voteServiceUnderTest.voteComments(request);
    }

    @Test
    void testVoteCommentsThenReturnResourceNotFound() {
        final VoteDto request = new VoteDto(CommentsVoteType.HELPFUL, 1L);

        final Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setText("text");
        comment1.setUser(new User());
        comment1.setReview(new Review());
        comment1.setVoteCount(1);

        final Optional<Comment> comment = Optional.of(comment1);
        when(mockCommentRepository.findById(0L)).thenReturn(comment);

        //final Optional<Vote> vote = Optional.of(new Vote(CommentsVoteType.HELPFUL, 1L , comment1, new User(), new Answer()));//new constructor
        //when(mockVoteRepository.findTopByCommentAndUserOrderByIdDesc(new Comment(), new User())).thenReturn(vote);

        when(mockVoteRepository.findTopByCommentAndUserOrderByIdDesc(any(Comment.class), eq(new User()))).thenReturn(Optional.of(new Vote()));
        when(mockAuthService.getCurrentUser()).thenReturn(new User());
        when(mockVoteMapper.map(any(VoteDto.class), any(Comment.class), eq(new User()))).thenReturn(new Vote());
        when(mockVoteRepository.save(any(Vote.class))).thenReturn(new Vote());
        when(mockCommentRepository.save(any(Comment.class))).thenReturn(new Comment());

        voteServiceUnderTest.voteComments(request);
    }

    @Test
    void testVoteAnswers() {

        final VoteAnswersDto request = new VoteAnswersDto(AnswersVoteType.UPVOTE, 1L);

        when(mockAnswerRepository.findById(1L)).thenReturn(Optional.of(new Answer()));
        when(mockVoteRepository.findTopByAnswerAndUserOrderByIdDesc(any(Answer.class), eq(new User()))).thenReturn(Optional.of(new Vote()));
        when(mockAuthService.getCurrentUser()).thenReturn(new User());
        when(mockVoteMapper.map2(any(VoteAnswersDto.class), any(Answer.class), eq(new User()))).thenReturn(new Vote());
        when(mockVoteRepository.save(any(Vote.class))).thenReturn(new Vote());
        when(mockAnswerRepository.save(any(Answer.class))).thenReturn(new Answer());


        voteServiceUnderTest.voteAnswers(request);
    }
}
