package com.westeros.diagnostics;

import com.westeros.diagnostics.runners.IDiagnose;
import com.westeros.diagnostics.services.contract.Diagnostics;
import com.westeros.moviesclient.IMoviesDictionariesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TheMovieDbApiConnectivityDiagnostics implements IDiagnose {

    private final IMoviesDictionariesClient moviesDictionariesClient;

    @Override
    public String getName() {
        return "TheMovieDb API Connectivity";
    }

    @Override
    public String getDescription() {
        return "Checks connectivity with TheMovieDB provider by fetching minimal data (languages).";
    }

    @Override
    public Diagnostics run() {
        Diagnostics diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try {

            var languages = moviesDictionariesClient.getLanguages();

            if (languages != null && !languages.isEmpty()) {
                diagnostics.setSuccess(true);
                diagnostics.setErrorMessage("Connected to TheMovieDB API. Languages fetched: " + languages.size());
            } else {
                diagnostics.setSuccess(false);
                diagnostics.setErrorMessage("Connected, but returned empty data.");
            }
        } catch (Exception e) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage("Failed to connect to TheMovieDB: " + e.getMessage());
        }

        return diagnostics;
    }
}