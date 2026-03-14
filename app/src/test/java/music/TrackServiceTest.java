// package music;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// import music.track.domain.Track;
// import music.track.dto.TrackRequestDTO;
// import music.track.dto.TrackResponseDTO;
// import music.track.dto.TrackUpdateDTO;

// import music.track.service.TrackService;
// import music.track.repository.InMemoryTrackRepository;
// import music.track.repository.InMemorySearchTrackRepository;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;



// public class TrackServiceTest {

    

//     // ── Test fixtures ────────────────────────────────────────────────────────────

//     private TrackService trackService;
//     private InMemoryTrackRepository trackRepository;
//     private InMemorySearchTrackRepository searchRepository;

//     @BeforeEach
//     void setUp() {
//         trackRepository = new InMemoryTrackRepository();
//         searchRepository = new InMemorySearchTrackRepository();
//         trackService = new TrackService(trackRepository, searchRepository);
//     }

//     // ── helpers ──────────────────────────────────────────────────────────────────

//     private TrackRequestDTO buildRequest(String title, String artist, String genre, int duration) {
//         TrackRequestDTO dto = new TrackRequestDTO();
//         dto.setTrackTitle(title);
//         dto.setArtist(artist);
//         dto.setGenre(genre);
//         dto.setDuration(duration);
//         return dto;
//     }

//     // ── createTrack ──────────────────────────────────────────────────────────────

//     @Test
//     void createTrack_validRequest_returnsTrackWithNonNullId() {
//         TrackRequestDTO dto = buildRequest("Song A", "Artist A", "Pop", 200);

//         Track result = trackService.createTrack(dto);

//         assertNotNull(result);
//         assertNotNull(result.getId());
//         assertFalse(result.getId().isBlank());
//     }

//     @Test
//     void createTrack_validRequest_fieldsAreMappedCorrectly() {
//         TrackRequestDTO dto = buildRequest("Song A", "Artist A", "Pop", 200);

//         Track result = trackService.createTrack(dto);

//         assertEquals("Song A", result.getTrackTitle());
//         assertEquals("Artist A", result.getArtist());
//         assertEquals("Pop", result.getGenre());
//         assertEquals(200, result.getDuration());
//     }

//     @Test
//     void createTrack_validRequest_createdAtIsSet() {
//         TrackRequestDTO dto = buildRequest("Song A", "Artist A", "Pop", 200);

//         Track result = trackService.createTrack(dto);

//         assertNotNull(result.getCreatedAt());
//     }

//     @Test
//     void createTrack_withReleaseDate_releaseDateIsSet() {
//         TrackRequestDTO dto = buildRequest("Song A", "Artist A", "Pop", 200);
//         LocalDate releaseDate = LocalDate.of(2023, 6, 15);
//         dto.setReleaseDate(releaseDate);

//         Track result = trackService.createTrack(dto);

//         assertEquals(releaseDate, result.getReleaseDate());
//     }

//     @Test
//     void createTrack_withoutReleaseDate_releaseDateIsNull() {
//         TrackRequestDTO dto = buildRequest("Song A", "Artist A", "Pop", 200);

//         Track result = trackService.createTrack(dto);

//         assertNull(result.getReleaseDate());
//     }

//     @Test
//     void createTrack_persistsToRepository() {
//         TrackRequestDTO dto = buildRequest("Song A", "Artist A", "Pop", 200);

//         Track result = trackService.createTrack(dto);

//         assertNotNull(trackRepository.getTrackById(result.getId()));
//     }

//     @Test
//     void createTrack_indexedInSearchRepository() {
//         TrackRequestDTO dto = buildRequest("Unique Title", "Artist A", "Pop", 200);

//         Track result = trackService.createTrack(dto);

//         assertNotNull(searchRepository.searchTitle(result.getTrackTitle()));
//     }

//     @Test
//     void createTrack_twoTracks_haveUniqueIds() {
//         Track t1 = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         Track t2 = trackService.createTrack(buildRequest("Song B", "Artist B", "Rock", 300));

//         assertNotEquals(t1.getId(), t2.getId());
//     }

//     // ── fromTrack ────────────────────────────────────────────────────────────────

//     @Test
//     void fromTrack_mapsAllFieldsToResponseDTO() {
//         TrackRequestDTO dto = buildRequest("Song A", "Artist A", "Pop", 200);
//         dto.setReleaseDate(LocalDate.of(2022, 1, 1));
//         Track track = trackService.createTrack(dto);

//         TrackResponseDTO response = trackService.fromTrack(track);

//         assertEquals(track.getId(), response.getTrackId());
//         assertEquals("Song A", response.getTrackTitle());
//         assertEquals("Artist A", response.getArtist());
//         assertEquals("Pop", response.getGenre());
//         assertEquals(200, response.getDuration());
//         assertEquals(track.getCreatedAt(), response.getCreatedAt());
//         assertEquals(LocalDate.of(2022, 1, 1), response.getReleaseDate());
//     }

//     // ── deleteTrack ──────────────────────────────────────────────────────────────

//     @Test
//     void deleteTrack_existingTrack_removesFromRepository() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));

//         trackService.deleteTrack(track.getId());

//         assertNull(trackRepository.getTrackById(track.getId()));
//     }

//     @Test
//     void deleteTrack_existingTrack_removesFromSearchIndex() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));

//         trackService.deleteTrack(track.getId());

//         assertEquals(new ArrayList<>(),searchRepository.searchTitle(track.getTrackTitle()));
//     }

//     @Test
//     void deleteTrack_nonExistingTrack_doesNotThrow() {
//         assertDoesNotThrow(() -> trackService.deleteTrack("non-existing-id"));
//     }

//     // ── updateTrack ──────────────────────────────────────────────────────────────

//     @Test
//     void updateTrack_updateTitle_titleIsUpdated() {
//         Track track = trackService.createTrack(buildRequest("Old Title", "Artist A", "Pop", 200));
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         updateDTO.setTitle("New Title");

//         TrackResponseDTO result = trackService.updateTrack(track.getId(), updateDTO);

//         assertEquals("New Title", result.getTrackTitle());
//     }

//     @Test
//     void updateTrack_updateArtist_artistIsUpdated() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Old Artist", "Pop", 200));
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         updateDTO.setArtist("New Artist");

//         TrackResponseDTO result = trackService.updateTrack(track.getId(), updateDTO);

//         assertEquals("New Artist", result.getArtist());
//     }

//     @Test
//     void updateTrack_updateGenre_genreIsUpdated() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         updateDTO.setGenre("Rock");

//         TrackResponseDTO result = trackService.updateTrack(track.getId(), updateDTO);

//         assertEquals("Rock", result.getGenre());
//     }

//     @Test
//     void updateTrack_updateDuration_durationIsUpdated() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         updateDTO.setDuration(350);

//         TrackResponseDTO result = trackService.updateTrack(track.getId(), updateDTO);

//         assertEquals(350, result.getDuration());
//     }

//     @Test
//     void updateTrack_updateReleaseDate_releaseDateIsUpdated() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         LocalDate newDate = LocalDate.of(2024, 3, 10);
//         updateDTO.setReleaseDate(newDate);

//         TrackResponseDTO result = trackService.updateTrack(track.getId(), updateDTO);

//         assertEquals(newDate, result.getReleaseDate());
//     }

//     @Test
//     void updateTrack_nullFields_doesNotOverwriteExistingValues() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         // all fields are null – nothing should change

//         TrackResponseDTO result = trackService.updateTrack(track.getId(), updateDTO);

//         assertEquals("Song A", result.getTrackTitle());
//         assertEquals("Artist A", result.getArtist());
//         assertEquals("Pop", result.getGenre());
//         assertEquals(200, result.getDuration());
//     }

//     @Test
//     void updateTrack_updatedAtIsSetAfterUpdate() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         updateDTO.setTitle("New Title");

//         trackService.updateTrack(track.getId(), updateDTO);

//         assertNotNull(trackRepository.getTrackById(track.getId()).getUpdatedAt());
//     }

//     @Test
//     void updateTrack_reindexedInSearchRepository() {
//         Track track = trackService.createTrack(buildRequest("Old Title", "Artist A", "Pop", 200));
//         System.out.println("Id: "+track.getId());
//         TrackUpdateDTO updateDTO = new TrackUpdateDTO();
//         updateDTO.setTitle("New Title");

//         trackService.updateTrack(track.getId(), updateDTO);

//         List<Track> searchResult = trackService.searchByTitle("New Title");
    
//         assertEquals(1, searchResult.size());
//         assertEquals("New Title", searchResult.get(0).getTrackTitle());
//     }

//     // ── searchByTitle ────────────────────────────────────────────────────────────

//     @Test
//     void searchByTitle_exactMatch_returnsMatchingTrack() {
//         trackService.createTrack(buildRequest("Blue Ocean", "Artist A", "Pop", 200));

//         List<Track> result = trackService.searchByTitle("Blue Ocean");

//         assertEquals(1, result.size());
//         assertEquals("Blue Ocean", result.get(0).getTrackTitle());
//     }

//     @Test
//     void searchByTitle_partialMatch_returnsMatchingTrack() {
//         trackService.createTrack(buildRequest("Blue Ocean", "Artist A", "Pop", 200));

//         List<Track> result = trackService.searchByTitle("Blue");

//         assertEquals(1, result.size());
//     }

//     @Test
//     void searchByTitle_caseInsensitive_returnsMatchingTrack() {
//         trackService.createTrack(buildRequest("Blue Ocean", "Artist A", "Pop", 200));

//         List<Track> result = trackService.searchByTitle("blue ocean");

//         assertEquals(1, result.size());
//     }

//     @Test
//     void searchByTitle_noMatch_returnsEmptyList() {
//         trackService.createTrack(buildRequest("Blue Ocean", "Artist A", "Pop", 200));

//         List<Track> result = trackService.searchByTitle("Nonexistent");

//         assertTrue(result.isEmpty());
//     }

//     @Test
//     void searchByTitle_multipleMatches_returnsAll() {
//         trackService.createTrack(buildRequest("Blue Ocean", "Artist A", "Pop", 200));
//         trackService.createTrack(buildRequest("Blue Sky", "Artist B", "Rock", 180));

//         List<Track> result = trackService.searchByTitle("Blue");

//         assertEquals(2, result.size());
//     }

//     // ── getTracksByArtist ────────────────────────────────────────────────────────

//     @Test
//     void getTracksByArtist_existingArtist_returnsAllTracks() {
//         trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         trackService.createTrack(buildRequest("Song B", "Artist A", "Rock", 180));
//         trackService.createTrack(buildRequest("Song C", "Artist B", "Jazz", 240));

//         List<Track> result = trackService.getTracksByArtist("Artist A");

//         assertEquals(2, result.size());
//         assertTrue(result.stream().allMatch(t -> "Artist A".equals(t.getArtist())));
//     }

//     @Test
//     void getTracksByArtist_nonExistingArtist_returnsEmptyList() {
//         trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));

//         List<Track> result = trackService.getTracksByArtist("Unknown Artist");

//         assertTrue(result.isEmpty());
//     }

//     // ── getTracksByGenre ─────────────────────────────────────────────────────────

//     @Test
//     void getTracksByGenre_existingGenre_returnsAllTracks() {
//         trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));
//         trackService.createTrack(buildRequest("Song B", "Artist B", "Pop", 180));
//         trackService.createTrack(buildRequest("Song C", "Artist C", "Rock", 240));

//         List<Track> result = trackService.getTracksByGenre("Pop");

//         assertEquals(2, result.size());
//         assertTrue(result.stream().allMatch(t -> "Pop".equals(t.getGenre())));
//     }

//     @Test
//     void getTracksByGenre_nonExistingGenre_returnsEmptyList() {
//         trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));

//         List<Track> result = trackService.getTracksByGenre("Classical");

//         assertTrue(result.isEmpty());
//     }

//     // ── getTrackById ─────────────────────────────────────────────────────────────

//     @Test
//     void getTrackById_existingId_returnsTrack() {
//         Track track = trackService.createTrack(buildRequest("Song A", "Artist A", "Pop", 200));

//         Track result = trackService.getTrackById(track.getId());

//         assertNotNull(result);
//         assertEquals(track.getId(), result.getId());
//     }

//     @Test
//     void getTrackById_nonExistingId_returnsNull() {
//         Track result = trackService.getTrackById("non-existing-id");

//         assertNull(result);
//     }
// }
