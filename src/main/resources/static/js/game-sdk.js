(function (window) {
  async function submitScore(payload) {
    if (!payload || !payload.gameSlug || typeof payload.score !== "number") {
      throw new Error("GameSDK.submitScore exige gameSlug e score numerico.");
    }

    const token = window.localStorage.getItem("educagames.token");
    if (!token) {
      throw new Error("Usuario nao autenticado.");
    }

    const response = await fetch("/api/scores/submit", {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
      },
      body: JSON.stringify({
        gameSlug: payload.gameSlug,
        score: Math.round(payload.score)
      })
    });

    const result = await response.json();
    if (!response.ok) {
      throw new Error(result.message || "Nao foi possivel enviar a pontuacao.");
    }

    if (window.parent && window.parent !== window) {
      window.parent.postMessage({
        type: "GAME_SCORE_SUBMITTED",
        payload: result
      }, window.location.origin);
    }

    return result;
  }

  window.GameSDK = {
    submitScore
  };
})(window);
