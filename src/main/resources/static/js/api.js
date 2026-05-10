(function (window) {
  const TOKEN_KEY = "educagames.token";
  const USER_KEY = "educagames.user";

  function getToken() {
    return window.localStorage.getItem(TOKEN_KEY);
  }

  function setSession(authResponse) {
    window.localStorage.setItem(TOKEN_KEY, authResponse.token);
    window.localStorage.setItem(USER_KEY, JSON.stringify(authResponse.user));
  }

  function clearSession(redirect) {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.removeItem(USER_KEY);
    if (redirect) {
      window.location.href = "/login";
    }
  }

  function getStoredUser() {
    const raw = window.localStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  }

  async function request(path, options) {
    const opts = options || {};
    const headers = new Headers(opts.headers || {});
    headers.set("Accept", "application/json");

    if (opts.body && !(opts.body instanceof FormData)) {
      headers.set("Content-Type", "application/json");
    }

    const token = getToken();
    if (token) {
      headers.set("Authorization", "Bearer " + token);
    }

    const response = await fetch("/api" + path, {
      ...opts,
      headers
    });

    if (response.status === 204) {
      return null;
    }

    const text = await response.text();
    const payload = text ? JSON.parse(text) : null;

    if (!response.ok) {
      if (response.status === 401) {
        clearSession(false);
      }
      const error = new Error((payload && payload.message) || "Falha na requisicao.");
      error.payload = payload;
      error.status = response.status;
      throw error;
    }

    return payload;
  }

  async function login(usernameOrEmail, password) {
    const auth = await request("/auth/login", {
      method: "POST",
      body: JSON.stringify({ usernameOrEmail, password })
    });
    setSession(auth);
    return auth;
  }

  async function register(data) {
    const auth = await request("/auth/register", {
      method: "POST",
      body: JSON.stringify(data)
    });
    setSession(auth);
    return auth;
  }

  async function requireAuth() {
    if (!getToken()) {
      window.location.href = "/login?redirect=" + encodeURIComponent(window.location.pathname);
      return null;
    }

    try {
      const user = await request("/users/me");
      window.localStorage.setItem(USER_KEY, JSON.stringify(user));
      updateNav();
      return user;
    } catch (error) {
      window.location.href = "/login?redirect=" + encodeURIComponent(window.location.pathname);
      return null;
    }
  }

  function updateNav() {
    const token = getToken();
    document.querySelectorAll("[data-auth='signed-in']").forEach((element) => {
      element.classList.toggle("hidden", !token);
    });
    document.querySelectorAll("[data-auth='signed-out']").forEach((element) => {
      element.classList.toggle("hidden", Boolean(token));
    });
  }

  function bindLogoutButtons() {
    document.querySelectorAll("[data-logout]").forEach((button) => {
      button.addEventListener("click", async () => {
        try {
          await request("/auth/logout", { method: "POST" });
        } catch (error) {
          // Logout is client-side for JWT; the API call is intentionally best effort.
        }
        clearSession(true);
      });
    });
  }

  document.addEventListener("DOMContentLoaded", () => {
    updateNav();
    bindLogoutButtons();
  });

  window.EducaAPI = {
    request,
    login,
    register,
    requireAuth,
    getToken,
    getStoredUser,
    clearSession,
    updateNav
  };
})(window);
