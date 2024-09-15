import Cookies from 'js-cookie';

export function setCookie(name, value, days) {
    return Cookies.set(name, value, { expires: days });
}

export function getCookie(name) {
    return Cookies.get(name);
}

export function removeCookie(name) {
    return Cookies.remove(name);
}