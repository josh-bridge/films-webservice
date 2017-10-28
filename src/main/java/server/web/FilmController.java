package server.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import server.Film;
import server.FilmInfo;
import server.result.Result;

public abstract class FilmController {

    private final FilmInfo filmInfo;

    public FilmController(FilmInfo filmInfo) {
        this.filmInfo = filmInfo;
    }

    @RequestMapping("/details/{id}")
    abstract String details(int id, HttpServletResponse httpResponse);

    @RequestMapping("/all")
    abstract String all(HttpServletResponse httpResponse);

    FilmInfo getFilmInfo() {
        return filmInfo;
    }

    Result<Film> getResult(int id) {
        if (id < 0) {
            return Result.emptyResult();
        }

        return filmInfo.getById(id)
                .map(Result::from)
                .orElseGet(Result::emptyResult);
    }
}
