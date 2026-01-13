package com.westeros.diagnostics.runners;

import com.westeros.diagnostics.services.contract.Diagnostics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnosticsRunner implements IRunDiagnoses {

    private final List<IDiagnose> diagnoses;
    @Override
    public List<Diagnostics> runAll() {
        return diagnoses.stream().map(IDiagnose::run).collect(Collectors.toList());
    }
}
